import { userConstants } from '../_constants';
import { userService } from '../_services';
import { alertActions } from './';
import { history } from '../_helpers';

export const userActions = {
    login,
    getNotifications,
    getProfile,
    getMemberProfile,
    updateProfile,
    logout,
    register,
    getUserLanguages,
    getAllLanguages,
    addLanguage,
    removeLanguage,
    changeActiveLanguage,
    search,
    userSearch,
    clearUserSearch,
    clearSearch,
    sendMessage,
    getConversations,
    getConversation,
    clearMessageSent,
    clearActiveConversation,
    notificationsSeen
};

function login(username, password) {
    return dispatch => {
        dispatch(request({ username }));

        userService.login(username, password)
            .then(
                user => {
                    dispatch(success(user));
                    history.push('/');
                },
                error => {
                    dispatch(failure(error.toString()));
                    dispatch(alertActions.error(error.toString()));
                }
            );
    };

    function request(user) { return { type: userConstants.LOGIN_REQUEST, user } }
    function success(user) { return { type: userConstants.LOGIN_SUCCESS, user } }
    function failure(error) { return { type: userConstants.LOGIN_FAILURE, error } }
}

function logout() {
    userService.logout();
    return { type: userConstants.LOGOUT };
}

function register(user) {
    return dispatch => {
        dispatch(request(user));

        userService.register(user)
            .then(
                user => {
                    dispatch(success(user));
                    history.push('/');
                },
                error => {
                    dispatch(failure(error.toString()));
                    dispatch(alertActions.error(error.toString()));
                }
            );
    };

    function request(user) { return { type: userConstants.REGISTER_REQUEST, user } }
    function success(user) { return { type: userConstants.LOGIN_SUCCESS, user } }
    function failure(error) { return { type: userConstants.REGISTER_FAILURE, error } }
}

function getNotifications() {
    return dispatch => {
        userService.getNotifications()
            .then(
                result => {
                    dispatch(success(result));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(result) { return { type: userConstants.NOTIFICATION_SUCCESS, result } }
    function failure(error) { return { type: userConstants.NOTIFICATION_FAILURE, error } }
}

function notificationsSeen(notifications) {
    return dispatch => {
        userService.notificationsSeen(notifications)
            .then(
                result => {
                    dispatch(success(result));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(result) { return { type: userConstants.NOTIFICATION_SUCCESS, result } }
    function failure(error) { return { type: userConstants.NOTIFICATION_FAILURE, error } }
}

function getProfile() {
    return dispatch => {
        userService.getProfile()
            .then(
                profile => {
                    dispatch(success(profile));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(profile) { return { type: userConstants.PROFILE_SUCCESS, profile } }
    function failure(error) { return { type: userConstants.PROFILE_FAILURE, error } }
}

function getMemberProfile(memberId) {
    return dispatch => {
        userService.getMemberProfile(memberId)
            .then(
                memberProfile => {
                    dispatch(success(memberProfile));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(profile) { return { type: userConstants.MEMBER_PROFILE_SUCCESS, profile } }
    function failure(error) { return { type: userConstants.MEMBER_PROFILE_FAILURE, error } }
}

function updateProfile(newProfile) {
    return dispatch => {
        userService.updateProfile(newProfile)
            .then(
                token => {
                    userService.getProfile()
                        .then(
                            profile => {
                                dispatch(success(profile));
                            },
                            error => {
                                dispatch(failure(error.toString()));
                            }
                        );
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(profile) { return { type: userConstants.UPDATE_PROFILE_SUCCESS, profile } }
    function failure(error) { return { type: userConstants.UPDATE_PROFILE_FAILURE, error } }
}

function getUserLanguages() {
    return dispatch => {
        userService.getUserLanguages()
            .then(
                languages => {
                    dispatch(success(languages));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(languages) { return { type: userConstants.LANGUAGE_SUCCESS, languages } }
    function failure(error) { return { type: userConstants.LANGUAGE_FAILURE, error } }
}

function getAllLanguages() {
    return dispatch => {
        userService.getAllLanguages()
            .then(
                languages => {
                    dispatch(success(languages));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(languages) { return { type: userConstants.ALL_LANGUAGES_SUCCESS, languages } }
    function failure(error) { return { type: userConstants.ALL_LANGUAGES_FAILURE, error } }
}

function addLanguage(language) {
    return dispatch => {
        userService.addLanguage(language)
            .then(
                profile => {
                    dispatch(success(profile));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(profile) { return { type: userConstants.ADD_LANGUAGE_SUCCESS, profile } }
    function failure(error) { return { type: userConstants.ADD_LANGUAGE_FAILURE, error } }
}

function removeLanguage(language) {
    return dispatch => {
        userService.removeLanguage(language)
            .then(
                profile => {
                    dispatch(success(profile));
                    getProfile()(dispatch);
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(profile) { return { type: userConstants.REMOVE_LANGUAGE_SUCCESS, profile } }
    function failure(error) { return { type: userConstants.REMOVE_LANGUAGE_FAILURE, error } }
}

function changeActiveLanguage(language) {
    return dispatch => {
        dispatch(success(language));
        localStorage.setItem('activeLanguage', JSON.stringify(language));
    };

    function success(language) { return { type: userConstants.CHANGE_LANGUAGE_SUCCESS, language } }
}

function search(type, term, languageId) {
    return dispatch => {
        userService.search(type, term, languageId)
            .then(
                result => {
                    dispatch(success(result));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(result) { return { type: userConstants.SEARCH_SUCCESS, result } }
    function failure(error) { return { type: userConstants.SEARCH_FAILURE, error } }
}

function userSearch(username) {
    return dispatch => {
        userService.userSearch(username)
            .then(
                result => { 
                    dispatch(success(result));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(result) { return { type: userConstants.USER_SEARCH_SUCCESS, result } }
    function failure(error) { return { type: userConstants.USER_SEARCH_FAILURE, error } }
}

function clearUserSearch(){
    return dispatch => {
        dispatch(success());
    };
    function success() { return { type: userConstants.USER_SEARCH_CLEAR, } }
}

function clearSearch(){
    return dispatch => {
        dispatch(success());
    };

    function success() { return { type: userConstants.SEARCH_CLEAR, } }
}

function sendMessage(username,text){
    return dispatch => {
        userService.sendMessage(username,text)
            .then(
                result => { 
                    dispatch(success(result));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(result) { return { type: userConstants.SEND_MESSAGE_SUCCESS, result } }
    function failure(error) { return { type: userConstants.SEND_MESSAGE_FAILURE, error } }
}

function getConversations(){
    return dispatch => {
        userService.getConversations()
            .then(
                result => { 
                    dispatch(success(result));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(result) { return { type: userConstants.GET_CONVERSATIONS_SUCCESS, result } }
    function failure(error) { return { type: userConstants.GET_CONVERSATIONS_FAILURE, error } }
}

function getConversation(conversationId){
    return dispatch => {
        userService.getConversation(conversationId)
            .then(
                result => { 
                    dispatch(success(result));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };

    function success(result) { return { type: userConstants.GET_CONVERSATION_SUCCESS, result } }
    function failure(error) { return { type: userConstants.GET_CONVERSATION_FAILURE, error } }
}

function clearMessageSent(){
    return dispatch => {
        dispatch(success());
    };

    function success() { return { type: userConstants.SEND_MESSAGE_CLEAR, } }
}

function clearActiveConversation(){
    return dispatch => {
        dispatch(success());
    };

    function success() { return { type: userConstants.GET_CONVERSATION_CLEAR, } }
}