import config from 'config';
import { authHeader } from '../_helpers';

export const writingService = {
  getWriting,
  submitWriting,
  scoreWriting,
  getnonCompletedAssignments,
  getCompletedAssignments,
  getWritingList
};

function scoreWriting(IdnScore) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...authHeader() },
        body: IdnScore.score
    };
    return fetch(`${config.apiUrl}/writing/score/${IdnScore.writingResultId}`, requestOptions)
        .then(handleResponse);
}

function getWritingList(languageId=1) {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/writing/getJson/${languageId}`, requestOptions)
        .then(handleResponse);
}

function getCompletedAssignments() {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/writing/completedAssignments`, requestOptions)
        .then(handleResponse);
}

function getnonCompletedAssignments() {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/writing/nonCompletedAssignments`, requestOptions)
        .then(handleResponse);
}

function getWriting(writingId) {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/writing/${writingId}`, requestOptions)
        .then(handleResponse);
}

function submitWriting(exercise) {
  const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', ...authHeader() },
      body: JSON.stringify({answerText: exercise.answer,evaluatorUsername: exercise.evaluatorUsername,writingId: exercise.writingId})
  };
  return fetch(`${config.apiUrl}/writing/${exercise.writingId}/submit`, requestOptions)
      .then(handleResponse);
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
