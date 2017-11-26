import { combineReducers } from 'redux'
import {
  REQUEST_POST,
  RECEIVED_POST,
  REQUEST_COMMENT,
  RECEIVED_COMMENT
} from '../actions/index'

export function posts(state = {}, action) {
  let post
  let copied
  switch (action.type) {
  case REQUEST_POST:
    return Object.assign({}, state, {
        isFetching: true,
    })
  case RECEIVED_POST:
    return Object.assign({}, state, {
      data: action.data,
      isFetching: false,
      error: action.error,
      receivedAt: action.receivedAt,
    })
  case REQUEST_COMMENT:
    copied = Object.assign({}, state)
    post = copied.data.find(target => target.id === action.postId)
    if (post) {
      post.isFetching = true
    }

    return copied;
  case RECEIVED_COMMENT:
    copied = Object.assign({}, state)
    post = copied.data.find(target => target.id === action.postId)
    if (post) {
      delete post.isFetching
      post.comments = post.comments.concat(action.data)
    }

    return copied;
  }
  return state
}


const rootReducer = combineReducers({
    posts,
})

export default rootReducer
