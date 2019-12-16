import { combineReducers } from 'redux';

import { authentication } from './authentication.reducer';
import { registration } from './registration.reducer';
import { users } from './users.reducer';
import { alert } from './alert.reducer';
import { quiz } from './quiz.reducer';
import { writing } from './writing.reducer';
import { comment } from './comment.reducer';

const rootReducer = combineReducers({
  authentication,
  registration,
  users,
  alert,
  quiz,
  writing,
  comment
});

export default rootReducer;