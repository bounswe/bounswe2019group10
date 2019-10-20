import config from 'config';
import { authHeader } from '../_helpers';

export const quizService = {
  getQuiz
};

function getQuiz(quizId) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/quiz/${quizId}`, requestOptions)
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