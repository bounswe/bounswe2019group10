import { writingConstants } from '../_constants';
import { writingService } from '../_services';
import { alertActions } from './';
import { history } from '../_helpers';

export const writingActions = {
  getWriting,
  submitWriting,
  scoreWriting,
  getnonCompletedAssignments,
  getCompletedAssignments,
  getWritingList
};

function getWriting(writingId){
  return dispatch => {
    writingService.getWriting(writingId)
        .then(
            writing => { 
              dispatch(success(writing))
            },
            error => {
              dispatch(failure(error.toString()));
            }
        );
  };
  function success(writing) { return { type: writingConstants.GET_WRITING, writing } }
  function failure(error) { return { type: writingConstants.GET_WRITING_FAILURE, error } }
}

function submitWriting(exercise){
  return dispatch => {
    writingService.submitWriting(exercise)
        .then(
            result => {
              dispatch(success(result))
            },
            error => {
              dispatch(failure(error.toString()));
            }
        );
  };
  function success(result) { return { type: writingConstants.SUBMIT_WRITING, result } }
  function failure(error) { return { type: writingConstants.SUBMIT_WRITING_FAILURE, error } }
}

function scoreWriting(IdnScore){
  return dispatch => {
    writingService.scoreWriting(IdnScore)
        .then(
            result => {
              dispatch(success(result))
            },
            error => {
              dispatch(failure(error.toString()));
            }
        );
  };
  function success(result) { return { type: writingConstants.SCORE_WRITING, result } }
  function failure(error) { return { type: writingConstants.SCORE_WRITING_FAILURE, error } }
}
function getnonCompletedAssignments(){
  return dispatch => {
    writingService.getnonCompletedAssignments()
        .then(
            result => {
              dispatch(success(result))
            },
            error => {
              dispatch(failure(error.toString()));
            }
        );
  };
  function success(result) { return { type: writingConstants.GET_ASSIGN_WRITING, result } }
  function failure(error) { return { type: writingConstants.GET_ASSIGN_WRITING_FAILURE, error } }
}
function getCompletedAssignments(){
  return dispatch => {
    writingService.getCompletedAssignments()
        .then(
            result => {
              dispatch(success(result))
            },
            error => {
              dispatch(failure(error.toString()));
            }
        );
  };
  function success(result) { return { type: writingConstants.GET_CASSIGN_WRITING, result } }
  function failure(error) { return { type: writingConstants.GET_CASSIGN_WRITING_FAILURE, error } }
}
function getWritingList(languageId){
  return dispatch => {
    writingService.getWritingList(languageId)
        .then(
            result => {
              dispatch(success(result))
            },
            error => {
              dispatch(failure(error.toString()));
            }
        );
  };
  function success(result) { return { type: writingConstants.GET_LIST_WRITING, result } }
  function failure(error) { return { type: writingConstants.GET_LIST_WRITING_FAILURE, error } }
}