var webpack = require("webpack");
var ExtractTextPlugin = require("extract-text-webpack-plugin");

module.exports = [
    {
        name: "browser",
        entry: {
            main: './src/client/main.js'
        },
        output: {
            path: './built/client',
            filename: 'bundle.js',
            publicPath: '/js/built/client/'
        },
        module: {
            loaders: [
                { test: /\.(js|jsx)$/, exclude: /node_modules/, loader: 'babel',
                    query: {
                        cacheDirectory: true,
                        presets: ['react', 'es2015', 'stage-0'],
                        plugins: ['transform-runtime', 'add-module-exports']
                    }
                },
                { test: /\.css$/, loader: ExtractTextPlugin.extract("style-loader", "css-loader") },
                { test: /\.less$/, loader: ExtractTextPlugin.extract("style-loader", "css-loader!less-loader") },
                { test: /\.(eot|woff|woff2|ttf|svg|png|jpg|gif)([\?]?.*)$/, loader: 'url-loader' }
            ]
        },
        plugins: [
            new ExtractTextPlugin("styles.css"),
            new webpack.optimize.DedupePlugin(),
            new webpack.optimize.UglifyJsPlugin({
                compress: {
                    warnings: false
                }
            }),
			new webpack.ProvidePlugin({
				'fetch': 'imports?this=>global!exports?global.fetch!whatwg-fetch'
			}),
            new webpack.DefinePlugin({
                "process.env": {
                    NODE_ENV: JSON.stringify("production")
                }
            })
            
        ],
        externals: {
            "jquery": "jQuery"
        }
    }
];
