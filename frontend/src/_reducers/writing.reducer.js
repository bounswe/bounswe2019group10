import { writingConstants } from '../_constants';

export function writing(state = {}, action) {
  switch (action.type) {
    case writingConstants.GET_WRITING:
      return { writing: action.writing };
    case writingConstants.GET_WRITING_FAILURE:
      return { error: action.error };
    case writingConstants.SUBMIT_WRITING:
        return { result: action.result };
    case writingConstants.SUBMIT_WRITING_FAILURE:
        return { error: action.error };    
        
    default:
      return state
  }
}