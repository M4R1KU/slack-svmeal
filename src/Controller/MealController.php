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
                $offset = $text == null || $text < 0 ? 0 : $text;

                if ($this->checkDayOffset($offset)) {
                    $response = $this->http->get('https://svmeal-api.jmnw.me/api/restaurant/bit/meal/' . $offset);
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
                    $result['text'] = 'Der Tageswert ' . $offset . ' ist nicht gültig.';
                    $result['attachments'][0] = [
                            'title' => 'Heute gültig:',
                            'color' => '#FF0000',
                            'text' => $this->getValidityString()
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

        /**
         * @param $offset int
         * @return bool
         */
        private function checkDayOffset($offset) {
            $currentWeekday = date('w');

            if (in_array($currentWeekday, [0, 6])) { // 0: Sunday, 6: Saturday
                // Allow all week days on weekends
                return $offset >= 0 && $offset <= 4;
            } else {
                // Since monday is one and friday is 5, this results in a linear decrease from four to zero
                // throughout the week, which is what we want
                $maxOffset = 5 - $currentWeekday;
                return $offset <= $maxOffset;
            }
        }

        /**
         * @return string
         */
        private function getValidityString() {
            $currentWeekday = date('w');

            if (5 == $currentWeekday) { // Friday
                return '0';
            } elseif (in_array($currentWeekday, [0, 6])) { // Weekend
                return '0 - 4';
            } else { // Monday - Thursday
                return '0 - ' . (5 - $currentWeekday);
            }
        }

        private function makeSlackJson($data) {
                $attachments = [];
                foreach($data['offers'] as $key => $offer) {
                        $fullMenu = implode("\n", $offer['trimmings']);

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
