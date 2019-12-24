import { userConstants } from '../_constants';
let activeLanguage = JSON.parse(localStorage.getItem('activeLanguage'));
const initialState = activeLanguage ? { activeLanguage: activeLanguage } : { activeLanguage: {}};

export function users(state = initialState, action) {
  switch (action.type) {
    case userConstants.GETALL_REQUEST:
      return {
        ...state,
        loading: true
      };
    case userConstants.GETALL_SUCCESS:
      return {
        ...state,
        items: action.users
      };
    case userConstants.GETALL_FAILURE:
      return { 
        ...state,
        error: action.error
      };
    case userConstants.DELETE_REQUEST:
      // add 'deleting:true' property to user being deleted
      return {
        ...state,
        items: state.items.map(user =>
          user.id === action.id
            ? { ...user, deleting: true }
            : user
        )
      };
    case userConstants.DELETE_SUCCESS:
      // remove deleted user from state
      return {
        ...state,
        items: state.items.filter(user => user.id !== action.id)
      };
    case userConstants.DELETE_FAILURE:
      // remove 'deleting:true' property and add 'deleteError:[error]' property to user 
      return {
        ...state,
        items: state.items.map(user => {
          if (user.id === action.id) {
            // make copy of user without 'deleting:true' property
            const { deleting, ...userCopy } = user;
            // return copy of user with 'deleteError:[error]' property
            return { ...userCopy, deleteError: action.error };
          }

          return user;
        })
      };
    case userConstants.NOTIFICATION_SUCCESS:
      return {
        ...state,
        notifications:action.result
      };
    case userConstants.NOTIFICATION_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.PROFILE_SUCCESS:
      return {
        ...state,
        profile:action.profile
      };
    case userConstants.PROFILE_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.MEMBER_PROFILE_SUCCESS:
      return {
        ...state,
        memberProfile: action.profile
      };
    case userConstants.MEMBER_PROFILE_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.UPDATE_PROFILE_SUCCESS:
        return {
          ...state,
          profileUpdated:true
        }
    case userConstants.UPDATE_PROFILE_FAILURE:
        return {
          ...state,
          error: action.error
        }
    case userConstants.LANGUAGE_SUCCESS:
      return {
        ...state,
        languages: action.languages
      };
    case userConstants.LANGUAGE_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.ALL_LANGUAGES_SUCCESS:
      return {
        ...state,
        all_languages: action.languages
      };
    case userConstants.ALL_LANGUAGES_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.CHANGE_LANGUAGE_SUCCESS:
      return {
        ...state,
        activeLanguage: action.language
      };
    case userConstants.SEARCH_SUCCESS:
      return {
        ...state,
        searchResults: action.result  
      }
    case userConstants.SEARCH_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.SEARCH_CLEAR:
      return {
        ...state,
        searchResults: []  
      };
    case userConstants.USER_SEARCH_SUCCESS:
      return {
        ...state,
        userSearchResults: action.result  
      }
    case userConstants.USER_SEARCH_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.USER_SEARCH_CLEAR:
      return {
        ...state,
        userSearchResults: []  
      };
    case userConstants.SEND_MESSAGE_SUCCESS:
      return {
        ...state,
        messageSent: action.result
      };
    case userConstants.SEND_MESSAGE_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.GET_CONVERSATIONS_SUCCESS:
      return {
        ...state,
        allConversations: action.result
      };
    case userConstants.GET_CONVERSATIONS_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.GET_CONVERSATION_SUCCESS:
      return {
        ...state,
        activeConversation: action.result
      };
    case userConstants.GET_CONVERSATION_FAILURE:
      return {
        ...state,
        error: action.error
      };
    case userConstants.SEND_MESSAGE_CLEAR:
      return {
        ...state,
        messageSent: ""
      };
    case userConstants.GET_CONVERSATION_CLEAR:
      return {
        ...state,
        activeConversation: []
      };
    default:
      return state
  }
}