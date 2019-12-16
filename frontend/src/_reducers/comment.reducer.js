import { commentConstants, writingConstants } from '../_constants';

export function comment(state = {}, action) {
    switch (action.type) {
        case commentConstants.GET_COMMENTS:
            return {};
        case commentConstants.GET_COMMENTS_FAILURE:
            return {};
        case commentConstants.GET_COMMENTS_FOR_MEMBER:
            return {};
        case commentConstants.GET_COMMENTS_FOR_MEMBER_FAILURE:
            return {};
        case commentConstants.MAKE_COMMENT:
            return {};
        case commentConstants.MAKE_COMMENT_FAILURE:
            return {};
        case commentConstants.DELETE_COMMENT:
            return {};
        case commentConstants.DELETE_COMMENT_FAILURE:
            return {};

        default:
            return
    }
}