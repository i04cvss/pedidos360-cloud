
import {
  PublicClientApplication,
  InteractionType,
  BrowserCacheLocation
} from '@azure/msal-browser';

import {
  MsalGuardConfiguration,
  MsalInterceptorConfiguration
} from '@azure/msal-angular';

export const msalConfig = {
  auth: {
    clientId: '32d53d2b-8785-4971-9b58-4352df50721e',
    authority: 'https://login.microsoftonline.com/925cecc5-99bd-4a32-b207-6ffd71daada4',
    redirectUri: 'http://32.200.81.80'
  },
  cache: {
    cacheLocation: BrowserCacheLocation.SessionStorage
  }
};

export const msalInstance = new PublicClientApplication(msalConfig);

export const loginRequest = {
  scopes: [
    'openid',
    'profile',
    'api://dfe3e673-ae97-437d-85ac-4f874cbd2a88/access_as_user'
  ]
};

export const msalGuardConfig: MsalGuardConfiguration = {
  interactionType: InteractionType.Redirect,
  authRequest: loginRequest
};

export const msalInterceptorConfig: MsalInterceptorConfiguration = {
  interactionType: InteractionType.Redirect,
  protectedResourceMap: new Map([
    [
      'http://32.200.81.80:8080/api/*',
      [
        'api://dfe3e673-ae97-437d-85ac-4f874cbd2a88/access_as_user'
      ]
    ]
  ])
};
