import { writingConstants } from '../_constants';
import { writingService } from '../_services';
import { alertActions } from './';
import { history } from '../_helpers';

export const writingActions = {
  getWriting,
  submitWriting
};

function getWriting(){
  return dispatch => {
    writingService.getWriting()
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

function submitWriting(writingId,answer){
  return dispatch => {
    writingService.submitWriting(writingId,answer)
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