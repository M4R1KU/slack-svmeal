<?php

namespace App\Controller;

use Cake\Core\Configure;
use Cake\Network\Exception\InternalErrorException;
use Cake\Network\Http\Client;

class OauthController extends AppController
{
    public function initialize()
    {
        $this->loadComponent('RequestHandler');
        $this->http = new Client();
    }

    public function oauth()
    {
        $code = $this->request->query('code');
        if ($code == null) {
            throw new InternalErrorException();
        }

        $response = $this->http->post('https://slack.com/api/oauth.access', [
            'client_id' => Configure::read('Security.oauth_client_id'),
            'client_secret' => Configure::read('Security.oauth_client_secret'),
            'code' => $code,
        ]);

        $data = $response->json;
        $response = [];
        if ($data['ok'] == true) {
            $response = ['status' => 'ok'];
        } else {
            $reponse = ['status' => 'error', 'error' => 'Authorization failed'];
        }
        $this->set('response', compact('response'));
        $this->set('_serialize', 'response');
    }
}
