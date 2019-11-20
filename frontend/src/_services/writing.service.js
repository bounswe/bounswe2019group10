import config from 'config';
import { authHeader } from '../_helpers';

export const writingService = {
  getWriting,
  submitWriting,
};

function getWriting() {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/writing/1`, requestOptions)
        .then(handleResponse);
}

function submitWriting(exercise) {
  const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', ...authHeader() },
      body: JSON.stringify({answerText: exercise.answer,evaluatorUsername: exercise.evaluatorUsername,writingId: exercise.writingId})
  };
  console.log(requestOptions);
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
