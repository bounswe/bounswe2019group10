import { commentConstants } from '../_constants';
import { commentService } from '../_services';

export const commentActions = {
    getComments,
    getCommentsForMember,
    // deleteComment,
    makeComment
};

function getComments() {
    return dispatch => {
        commentService.getComments()
            .then(
                comments => {
                    dispatch(success(comments));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };
    function success(comments) {
        return { type: commentConstants.GET_COMMENTS, comments }
    }
    function failure(error) {
        return { type: commentConstants.GET_COMMENTS_FAILURE, error }
    }
};

function getCommentsForMember(memberId) {
    return dispatch => {
        commentService.getCommentsForMember(memberId)
            .then(
                comments => {
                    dispatch(success(comments));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };
    function success(comments) {
        return { type: commentConstants.GET_COMMENTS_FOR_MEMBER, comments }
    }
    function failure(error) {
        return { type: commentConstants.GET_COMMENTS_FOR_MEMBER_FAILURE, error }
    }
}

function makeComment(memberComment) {
    console.log('HERE:', memberComment)
    return dispatch => {
        commentService.makeComment(memberComment)
            .then(
                comments => {
                    dispatch(success(comments));
                },
                error => {
                    dispatch(failure(error.toString()));
                }
            );
    };
    function success(comments) {
        return { type: commentConstants.MAKE_COMMENT, comments }
    }
    function failure(error) {
        return { type: commentConstants.MAKE_COMMENT_FAILURE, error }
    }
}

// function deleteComment(commentId) {

// }