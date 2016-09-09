<?php

namespace App\Controller;

use Cake\Network\Exception\InternalErrorException;
use Cake\Network\Exception\MethodNotAllowedException;
use Cake\Network\Http\Client;

class MealController extends AppController {

        private $http = null;
        private $colors = ['#FF714F', '#D2A38B', '#535866', '#4F7EFF'];

        public function initialize() {
                $this->loadComponent('RequestHandler');
                $this->http = new Client();
        }

        public function view() {
            if ($this->request->is('post')) {
                $result = [];
                $text = $this->request->data('text');
                $id = $text == null ? 0 : $text;

                $today = date('w');
                if (($today != 6 || $today != 0) && $id >= 0 && $id <= (5 - $today)) {
                    $response = $this->http->get('https://svmeal-api.jmnw.me/api/restaurant/bit/meal/' . $id);
                    if ($response->isOk() && $response->header('content-type') == 'application/json') {
                            $decodedJson = json_decode($response->body(), true);

                            if ($decodedJson['status'] != 'Ok') {
                                throw new InternalErrorException();
                            }
                            $result = $this->makeSlackJson($decodedJson['data']);
                    } else {
                            throw new InternalErrorException();
                    }
                } else {
                    $result['text'] = 'Der Tageswert ' . $id . ' ist nicht valide.';
                    $result['attachments'][0] = [
                            'title' => 'Heute valid:',
                            'color' => '#FF0000',
                            'text' => '0' . ($today != 5 ? ' - ' . (5 - $today) : '')
                    ];
                }
            } else {
                throw new MethodNotAllowedException();
            }
            $this->response->header('Access-Control-Allow-Origin', '*');
            $this->set([
                'text' => $result['text'],
                'attachments' => $result['attachments']
                ]);
            $this->set('_serialize', ['text', 'attachments']);
        }

        private function makeSlackJson($data) {
                $attachments = [];
                foreach($data['offers'] as $key => $offer) {
                        $fullMenu = implode(' ', $offer['trimmings']);

                        $attachments[] = [
                                'title' => $offer['description'],
                                'color' => $this->colors[$key],
                                'fields' => [
                                        [
                                                'title' => $offer['title'],
                                                'value' => $fullMenu,
                                                'short' => 'true'
                                        ],
                                        [
                                                'title' => 'Preis in CHF',
                                                'value' => $offer['price']['internalPrice'],
                                                'short' => true
                                        ]
                                ]
                        ];
                }
                return ['text' => 'Angebot vom ' . date('d.m.Y', strtotime($data['date'])), 'attachments' => $attachments];
        }

}



 ?>
