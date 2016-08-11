<?php

namespace App\Controller;

use Cake\Network\Exception\InternalErrorException;
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
                $text = $this->request->data('text');
                $id = $text == null ? 0 : $text;
                $response = $this->http->get('https://svmeal-api.jmnw.me/api/restaurant/bit/meal/' . $id);
                if ($response->isOk() && $response->header('content-type') == 'application/json') {
                        $decodedJson = json_decode($response->body(), true);

                        if ($decodedJson['status'] != 'Ok') {
                                throw new InternalErrorException();
                        }
                        $res = $this->makeSlackJson($decodedJson['data']);
                        $this->set([
                            'text' => $res['text'],
                            'attachments' => $res['attachments']
                            ]);
                        $this->set('_serialize', ['text', 'attachments']);

                } else {
                        throw new InternalErrorException();
                }
            } else {
                throw new MethodNotAllowedException();
            }
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
                return ['text' => 'Angebot vom ' . $data['date'], 'attachments' => $attachments];
        }

}



 ?>
