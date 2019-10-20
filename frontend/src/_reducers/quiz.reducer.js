import { quizConstants } from '../_constants';

export function quiz(state = {}, action) {
  switch (action.type) {
    case quizConstants.GET_QUIZ:
      return { quiz: action.quiz };
    case quizConstants.GET_QUIZ_FAILURE:
      return { error: action.error };
    case quizConstants.REGISTER_FAILURE:
      return {};
    default:
      return state
  }
}