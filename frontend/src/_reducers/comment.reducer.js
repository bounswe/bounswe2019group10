import { commentConstants } from '../_constants';

const initialState = {}

export function comment(state = initialState, action) {
    switch (action.type) {
        case commentConstants.GET_COMMENTS:
            return { comments: action.comment };
        case commentConstants.GET_COMMENTS_FAILURE:
            return { error: action.error };
        case commentConstants.GET_COMMENTS_FOR_MEMBER:
            return { comments: action.comment };
        case commentConstants.GET_COMMENTS_FOR_MEMBER_FAILURE:
            return { error: action.error };
        case commentConstants.MAKE_COMMENT:
            return { comments: action.comment };
        case commentConstants.MAKE_COMMENT_FAILURE:
            return { error: action.error };

        default:
            return state
    }
}