import { userConstants } from '../_constants';
import { userService } from '../_services';
import { alertActions } from './';
import { history } from '../_helpers';

export const userActions = {
    login,
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
    clearSearch
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
                profile => {
                    dispatch(success(profile));
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

function clearSearch() {
    return dispatch => {
        dispatch(success());
    };

    function success() { return { type: userConstants.SEARCH_CLEAR, } }
}