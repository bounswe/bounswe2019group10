import { authHeader,config } from '../_helpers';

export const quizService = {
  getQuiz,
  submitQuiz,
  getQuizes
};

function getQuiz(quizId) {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/quiz/${quizId}`, requestOptions)
        .then(handleResponse);
}

function submitQuiz(quizId,answers) {
  const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', ...authHeader() },
      body: JSON.stringify({answers: answers,quizId: quizId})
  };
  return fetch(`${config.apiUrl}/quiz/${quizId}/submit`, requestOptions)
      .then(handleResponse);
}

function getQuizes(activeLanguageId,level) {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/quiz/level/${level}/language/${activeLanguageId}`, requestOptions)
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