const path = require('path');
const WebpackShellPlugin = require('webpack-shell-plugin');

module.exports = [
  {
    entry: './src/main/js/index',
    output: {
      path: path.resolve(__dirname, 'dist'),
      filename: 'bundle.js',
      library: 'ReactNashornApp',
      libraryTarget: 'umd'
    },
    plugins: [
      new WebpackShellPlugin({
        onBuildStart:['rm -rf ./dist'],
        onBuildExit:['node ./script/copy.js']
      }),
    ],
    module: {
      rules: [
        {
          test: /\.jsx?$/,
          exclude: /(node_modules|bower_components)/,
          use: {
            loader: 'babel-loader',
            options: {
              presets: ['env', 'react']
            }
          }
        }
      ]
    },
    resolve: {
      extensions: ['.js', '.jsx']
    }
  },
  {
    entry: './src/main/js/index-server',
    output: {
      path: path.resolve(__dirname, './src/main/resources/script'),
      filename: 'server.js',
      library: 'ReactNashornApp',
    },
    module: {
      rules: [
        {
          test: /\.jsx?$/,
          exclude: /(node_modules|bower_components)/,
          use: {
            loader: 'babel-loader',
            options: {
              presets: ['env', 'react']
            }
          }
        }
      ]
    },
    resolve: {
      extensions: ['.js', '.jsx']
    }
  }
];
