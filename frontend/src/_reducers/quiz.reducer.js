import { quizConstants } from '../_constants';

export function quiz(state = {}, action) {
  switch (action.type) {
    case quizConstants.GET_QUIZ:
      return { statequiz: action.quiz.quiz };
    case quizConstants.GET_QUIZ_FAILURE:
      return { error: action.error };
    case quizConstants.SUBMIT_QUIZ:
        return { result: action.result };
    case quizConstants.SUBMIT_QUIZ_FAILURE:
        return { error: action.error };    
    case quizConstants.GET_QUIZES:
      return { quizList: action.result };
    case quizConstants.GET_QUIZES_FAILURE:
        return { error: action.error };    
    
    default:
      return state
  }
}