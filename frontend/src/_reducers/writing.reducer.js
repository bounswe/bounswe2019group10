import { writingConstants } from '../_constants';

export function writing(state = {}, action) {
  switch (action.type) {
    case writingConstants.GET_WRITING:
      return { ...state, writing: action.writing };
    case writingConstants.GET_WRITING_FAILURE:
      return { error: action.error };
    case writingConstants.SUBMIT_WRITING:
      return { ...state, result: action.result };
    case writingConstants.SUBMIT_WRITING_FAILURE:
      return { error: action.error };
    case writingConstants.SCORE_WRITING:
      return { ...state, result: action.result };
    case writingConstants.SCORE_WRITING_FAILURE:
      return { ...state, error: action.error };
    case writingConstants.GET_ASSIGN_WRITING:
      return { ...state, assignments: action.result };
    case writingConstants.GET_ASSIGN_WRITING_FAILURE:
      return { ...state, error: action.error };
    case writingConstants.GET_CASSIGN_WRITING:
      return { ...state, cassignments: action.result };
    case writingConstants.GET_CASSIGN_WRITING_FAILURE:
      return { ...state, error: action.error };
    case writingConstants.GET_WRITING_LIST:
      return { ...state, writingList: action.result };
    case writingConstants.GET_WRITING_LIST_FAILURE:
      return { ...state, error: action.error };
    case writingConstants.GET_WRITING_ANNOTATIONS:
      return { ...state, annotations: action.result };
    case writingConstants.GET_WRITING_ANNOTATIONS_FAILURE:
      return { ...state, error: action.error };
    case writingConstants.ANNOTATIONS_CLEAR:
      return { ...state, annotations: [] };
    case writingConstants.CREATE_WRITING_ANNOTATIONS:
      return { ...state, newAnnotation: action.result };
    case writingConstants.CREATE_WRITING_ANNOTATIONS_FAILURE:
      return { ...state, error: action.error };
    case writingConstants.NEW_ANNOTATION_CLEAR:
      return { ...state, newAnnotation: {} };
    case writingConstants.DELETE_ANNOTATION:
      return { ...state, deletedAnnotation: action.result };
    case writingConstants.DELETE_ANNOTATION_FAILURE:
      return { ...state, error: action.error };
    case writingConstants.DELETE_ANNOTATION_CLEAR:
      return { ...state, deletedAnnotation: "" };
    
    default:
      return state
  }
}