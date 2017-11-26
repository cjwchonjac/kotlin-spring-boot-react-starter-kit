import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import { fetchPosts, fetchComments } from '../actions/index'
import PostRenderer from './PostRenderer'

class PostApp extends Component {

  render() {
    const { dispatch, posts } = this.props
    return <div>
      <button onClick={() => dispatch(fetchPosts())}>Refresh</button>
      { posts.data && posts.data.map(post => <PostRenderer title={post.title}
                                        body={post.body}
                                        createdAt={post.createdAt}
                                        comments={post.comments}
                                        onLoadMoreClick={() => {
                                          dispatch(fetchComments(post.id, post.comments.length, 5))
                                        }}
                                        disableLoadButton={post.isFetching}
                                        key={post.id}>
      </PostRenderer>) }
    </div>
  }
}

PostApp.propTypes = {
  posts: PropTypes.object.isRequired,
}

function mapStateToProps(state) {
  const { posts } = state
  return {
    posts
  }
}

export default connect(mapStateToProps)(PostApp)
