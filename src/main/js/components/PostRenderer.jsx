import React from 'react'
import PropTypes from 'prop-types'

import CommentRenderer from './CommentRenderer'

const PostRenderer = (props) =>
<div>
  <h1>{ props.title }</h1>
  <div>{ props.body }</div>
  <p>{ props.createdAt }</p>
  <div>
    { props.comments.map(comment => <CommentRenderer key={comment.id}
                                                     message={comment.message}
                                                     createdAt={comment.createdAt} />) }
    <button disabled={props.disableLoadButton} onClick={() => {
      if (props.onLoadMoreClick) {
        props.onLoadMoreClick()
      }
    }} >load more..</button>
  </div>
</div>

PostRenderer.propTypes = {
  title: PropTypes.string.isRequired,
  body: PropTypes.string.isRequired,
  createdAt: PropTypes.string.isRequired,
  comments: PropTypes.array.isRequired,
  onLoadMoreClick: PropTypes.func,
  disableLoadButton: PropTypes.bool
}

export default PostRenderer
