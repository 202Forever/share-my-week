
import React, { Component, PropTypes } from 'react';

import { Grid } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import CreateWeekForm from '../containers/ShareMyWeek/CreateWeekForm.jsx';


class App extends Component {

    render() {
        return (
            <div>
                <div className="cover" params={ this.props.params }>
                    <div className="navbar" params={ this.props.params }>
                        <Grid params={ this.props.params }>
                            <div className="logo" params={ this.props.params }>
                                <img src="/images/sharemyweek-logo.png"
                                     width={ 280 }
                                     height={ 180 }
                                     className="center-block"
                                     params={ this.props.params }></img>
                            </div>
                        </Grid>
                    </div>
                    <div className="cover-image"
                         style={ {    backgroundImage: 'url(/images/landing-bg.jpg)'} }
                         params={ this.props.params }>
                        <span params={ this.props.params }></span>
                    </div>
                    <Grid params={ this.props.params }>
                        <Row params={ this.props.params }>
                            <Col xs={ 12 }
                                 md={ 12 }
                                 sm={ 12 }
                                 className="text-center"
                                 params={ this.props.params }>
                            <h2 className="text-inverse" params={ this.props.params }><span params={ this.props.params }>Share your availability with your friends instantly!</span></h2>
                            </Col>
                        </Row>
                        <Row params={ this.props.params }>
                            <Col xsHidden={ true }
                                 smHidden={ true }
                                 xs={ 3 }
                                 md={ 3 }
                                 sm={ 3 }
                                 lg={ 3 }
                                 params={ this.props.params }>
                            <span params={ this.props.params }></span>
                            </Col>
                            <Col xs={ 12 }
                                 md={ 6 }
                                 sm={ 12 }
                                 lg={ 6 }
                                 params={ this.props.params }>
                            <CreateWeekForm params={ this.props.params }></CreateWeekForm>
                            </Col>
                            <Col xsHidden={ true }
                                 smHidden={ true }
                                 xs={ 3 }
                                 md={ 3 }
                                 sm={ 3 }
                                 lg={ 3 }
                                 params={ this.props.params }>
                            <span params={ this.props.params }></span>
                            </Col>
                        </Row>
                    </Grid>
                </div>
            </div>
            );
    }
}

export default App;

