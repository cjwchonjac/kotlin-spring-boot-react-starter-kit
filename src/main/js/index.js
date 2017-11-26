import 'babel-polyfill'
import React from 'react'
import ReactDOM from 'react-dom'
import { createStore, applyMiddleware } from 'redux'
import { Provider } from 'react-redux'
import thunk from 'redux-thunk'
import { createLogger } from 'redux-logger'

import reducers from './reducers/index'
import PostApp from './components/PostApp'

export function render(elementId, preloadedState) {
  const store = createStore(reducers,
    preloadedState,
    applyMiddleware(thunk, createLogger()))

  ReactDOM.render(
    <Provider store={store}>
      <PostApp />
    </Provider>, document.getElementById(elementId))
}
