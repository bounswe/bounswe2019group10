import { authHeader,config } from '../_helpers';

export const writingService = {
  getWriting,
  submitWriting,
  scoreWriting,
  getnonCompletedAssignments,
  getCompletedAssignments,
  getWritingList,
  getWritingAnnotations,
  createAnnotation,
  deleteAnnotation,
  getMyWritings,
  submitWritingTopic,
  uploadWritingImage,
  createImageAnnotation,
  getWritingImageAnnotations,
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
function getMyWritings() {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader() }
    };
    return fetch(`${config.apiUrl}/writing/scores`, requestOptions)
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
    const requestOptions1 = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', ...authHeader() },
      body: JSON.stringify({imageURL: exercise.imageUrl,evaluatorUsername: exercise.evaluatorUsername,writingId: exercise.writingId})
    };
    const requestOptions2 = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...authHeader() },
        body: JSON.stringify({answerText: exercise.answer,evaluatorUsername: exercise.evaluatorUsername,writingId: exercise.writingId})  
    };
  if(exercise.answerType==="picture"){
  return fetch(`${config.apiUrl}/writing/${exercise.writingId}/submitWithImageURL`, requestOptions1)
    .then(handleResponse);
  }else if(exercise.answerType==="text"){
  return fetch(`${config.apiUrl}/writing/${exercise.writingId}/submit`, requestOptions2)
      .then(handleResponse);
  }}

function getWritingAnnotations(writingResultId){
    const requestOptions = {
      method: 'GET',
      headers: { 'Content-Type': 'application/json', ...authHeader() },
    };
    return fetch(`${config.apiUrl}/annotation/all/${writingResultId}`, requestOptions)
        .then(handleResponse);
}

function createAnnotation(writingResultId, text, start,end) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...authHeader() },
        body: JSON.stringify({writingResultId: writingResultId,posStart: start,posEnd: end,annotationText:text})
    };
    return fetch(`${config.apiUrl}/annotation/create`, requestOptions)
        .then(handleResponse);
}

function deleteAnnotation(writingResultId){
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded', ...authHeader() },
    };
    return fetch(`${config.apiUrl}/annotation/delete?id=${writingResultId}`, requestOptions);
}

function createImageAnnotation(annotation){
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...authHeader() },
        body: JSON.stringify(annotation)
    };
    return fetch(`${config.apiUrl}/annotation/image/create`, requestOptions)
        .then(handleResponse);
}

function getWritingImageAnnotations(imageUrl){
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded', ...authHeader() },
    };
    return fetch(`${config.apiUrl}/annotation/image/all?url=${imageUrl}`, requestOptions)
        .then(handleResponse);
}

function submitWritingTopic(newTopic) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...authHeader() },
        body: JSON.stringify({writingName: newTopic.questionTitle,taskText: newTopic.questionBody,languageId: newTopic.language})
    };
    return fetch(`${config.apiUrl}/writing/add`, requestOptions)
        .then(handleResponse);
  }

function uploadWritingImage(upload) {
    const requestOptions = {
        method: 'POST',
        headers: { ...authHeader() },
        body: upload
    };
    
    return fetch(`${config.apiUrl}/writing/uploadWritingImage`, requestOptions)
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
