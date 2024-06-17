const authorizationCode = () => {
  window.location = new URL('http://localhost:8081/oauth2/authorization/keycloak');
}

const password = () => {
  window.location = new URL('http://localhost:8081/oauth2/authorization/keycloak2');
};

const clientCredentials = () => {
  window.location = new URL('http://localhost:8081/oauth2/authorization/keycloak3');
};
