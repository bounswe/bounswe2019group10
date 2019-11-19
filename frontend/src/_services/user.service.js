import config from 'config';
import { authHeader } from '../_helpers';

export const userService = {
    login,
    logout,
    register,
    getProfile,
    getUserLanguages,
    getAllLanguages,
    addLanguage,
    getAll,
    getById,
    update,
    delete: _delete
};

function login(username, password) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    };

    return fetch(`${config.apiUrl}/authenticate`, requestOptions)
        .then(handleResponse)
        .then(user => {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('user', JSON.stringify(user));

            return user;
        });
}

function logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('user');
}

function getProfile(){
    const requestOptions = {
        method: 'GET',
        headers: {...authHeader(),"Access-Control-Allow-Origin":"*"}
    };
    return fetch(`${config.apiUrl}/member/profile`, requestOptions).then(handleResponse);
}

function getUserLanguages(){
    const requestOptions = {
        method: 'GET',
        headers: {...authHeader(),"Access-Control-Allow-Origin":"*"}
    };
    return fetch(`${config.apiUrl}/member/languages`, requestOptions).then(handleResponse);
}

function getAllLanguages(){
    const requestOptions = {
        method: 'GET',
        headers: {...authHeader(),"Access-Control-Allow-Origin":"*"}
    };
    return fetch(`${config.apiUrl}/lang`, requestOptions).then(handleResponse);
}

function addLanguage(language){
    console.log(JSON.stringify([language]));
    const requestOptions = {
        method: 'POST',
        headers: {...authHeader(),"Access-Control-Allow-Origin":"*",'Content-Type': 'application/json'},
        body: JSON.stringify([language])
    };
    return fetch(`${config.apiUrl}/member/addlang`, requestOptions).then(handleResponse);
}

function getAll() {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/users`, requestOptions).then(handleResponse);
}

function getById(id) {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/users/${id}`, requestOptions).then(handleResponse);
}

function register(user) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    };
    return fetch(`${config.apiUrl}/register`, requestOptions)
        .then(handleResponse)
            .then(user => {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('user', JSON.stringify(user));
                return user;
        });
}

function update(user) {
    const requestOptions = {
        method: 'PUT',
        headers: { ...authHeader(), 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    };

    return fetch(`${config.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse);;
}

// prefixed function name with underscore because delete is a reserved word in javascript
function _delete(id) {
    const requestOptions = {
        method: 'DELETE',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/users/${id}`, requestOptions).then(handleResponse);
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}