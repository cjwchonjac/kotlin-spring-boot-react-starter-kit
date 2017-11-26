import fetch from 'isomorphic-fetch'

export const REQUEST_POST = 'REQUEST_POST'
export const RECEIVED_POST = 'RECEIVED_POST'
export const REQUEST_COMMENT = 'REQUEST_COMMENT'
export const RECEIVED_COMMENT = 'RECEIVED_COMMENT'

function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
      return response
  } else {
      const error = new Error(response.statusText)
      error.response = response
      throw error
  }
}

export function requestPosts() {
  return {
    type: REQUEST_POST,
  }
}

export function receivedPosts(data, error) {
  return {
    type: RECEIVED_POST,
    data,
    error,
    receivedAt: new Date()
  }
}

export function fetchPosts() {
  return (dispatch) => {
      dispatch(requestPosts())

      return fetch('/api/post/list', {
          method: 'GET',
          credentials: 'same-origin',
      }).then(response => checkStatus(response))
        .then(response => response.json())
        .then(json => {
          dispatch(receivedPosts(json))
        })
        .catch(error => {
          dispatch(receivedPosts(undefined, error))
        })
  }
}

export function requestComments(postId) {
  return {
    type: REQUEST_COMMENT,
    postId,
  }
}

export function receivedComments(comments, postId, error) {
  return {
    type: RECEIVED_COMMENT,
    data: comments,
    postId,
    error,
    receivedAt: new Date()
  }
}

export function fetchComments(postId, offset, limit) {
  return (dispatch) => {
    dispatch(requestComments(postId))

    return fetch(`/api/post/comments?postId=${encodeURIComponent(postId)}&offset=${encodeURIComponent(offset)}&limit=${encodeURIComponent(limit)}`, {
          method: 'GET',
          credentials: 'same-origin',
        }).then(response => checkStatus(response))
          .then(response => response.json())
          .then(json => {
            dispatch(receivedComments(json, postId))
           })
           .catch(error => {
          dispatch(requestComments(undefined, postId, error))
        })
  }
}
