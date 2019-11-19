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
    return fetch(`${config.apiUrl}/random-writing-topic`, requestOptions)
        .then(handleResponse);
}

function submitWriting(writingId,answer) {
  const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', ...authHeader() },
      body: JSON.stringify({answer: answer,writingId: writingId})
  };
  return fetch(`${config.apiUrl}/writing/${writingId}/submit`, requestOptions)
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
