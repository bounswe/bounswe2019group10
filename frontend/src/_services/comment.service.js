import { authHeader, config } from '../_helpers';

export const commentService = {
    getComments,
    getCommentsForMember,
    // deleteComment,
    makeComment
};

function getComments() {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/comment/`, requestOptions)
        .then(handleResponse);
}

function getCommentsForMember(memberId) {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() },
        body: JSON.stringify({ memberId: memberId })
    };
    return fetch(`${config.apiUrl}/comment/${memberId}`, requestOptions)
        .then(handleResponse);
}

function makeComment(memberComment) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...authHeader() },
        body: JSON.stringify({ memberCommentDTO: memberComment })
    };
    return fetch(`${config.apiUrl}/comment/make`, requestOptions)
        .then(handleResponse);
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if(!response.ok) {
            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }
        return data;
    });
}

//TODO: HandleResponse a girmiyo
// function deleteComment(commentId) {
//     const requestOptions = {
//         method: 'POST',
//         headers: { 'Content-Type': 'application/json', ...authHeader() },
//         body: JSON.stringify({ id: commentId })
//     };
//     return fetch(`${config.apiUrl}/comment/delete`, requestOptions);
// }