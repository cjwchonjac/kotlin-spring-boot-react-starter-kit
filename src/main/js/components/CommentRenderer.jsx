import React from 'react'
import PropTypes from 'prop-types'

const CommentRenderer = (props) =>
    <div>
      <p>{ props.message }</p>
      <p>{ props.createdAt }</p>
    </div>

CommentRenderer.propTypes = {
  message: PropTypes.string.isRequired,
  createdAt: PropTypes.string.isRequired,
}

export default CommentRenderer
