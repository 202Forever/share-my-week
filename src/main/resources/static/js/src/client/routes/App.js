
import React, { Component, PropTypes } from 'react';

import { Navbar } from 'react-bootstrap';
import { Nav } from 'react-bootstrap';
import { NavItem } from 'react-bootstrap';
import FeatureList from '../components/ShareMyWeek/FeatureList.jsx';


class App extends Component {

    render() {
        return (
            <div>
                <Navbar staticTop={ true }
                        toggleNavKey={ 0 }
                        brand="202Forever"
                        params={ this.props.params }>
                    <Nav pullRight={ true } params={ this.props.params }>
                        <NavItem href="#"
                                 active={ true }
                                 params={ this.props.params }>
                            <span params={ this.props.params }>Home</span>
                        </NavItem>
                        <NavItem href="#" params={ this.props.params }>
                            <span params={ this.props.params }>Contact Us</span>
                        </NavItem>
                    </Nav>
                </Navbar>
                <div className="section" params={ this.props.params }>
                    <div className="container" params={ this.props.params }>
                        <div className="row" params={ this.props.params }>
                            <div className="col-md-12 text-center" params={ this.props.params }>
                                <h1 params={ this.props.params }><small params={ this.props.params }>Start planning your week with others.</small></h1>
                                <h1 className="text-center text-primary" params={ this.props.params }><span params={ this.props.params }>ShareMyWeek</span></h1>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="section" params={ this.props.params }>
                    <div className="container" params={ this.props.params }>
                        <div className="row" params={ this.props.params }>
                            <div className="col-md-6" params={ this.props.params }>
                                <img src="http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png"
                                     className="hidden-sm hidden-xs img-responsive"
                                     params={ this.props.params }></img>
                            </div>
                            <div className="col-md-6" params={ this.props.params }>
                                <FeatureList className="media-list" params={ this.props.params }></FeatureList>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="section" params={ this.props.params }>
                    <div className="container" params={ this.props.params }>
                        <div className="row" params={ this.props.params }>
                            <div className="col-md-offset-3 col-md-6" params={ this.props.params }>
                                <h4 className="text-center" params={ this.props.params }><span params={ this.props.params }>Get a Week planner for you and your friends.</span></h4>
                                <form role="form" params={ this.props.params }>
                                    <div className="form-group" params={ this.props.params }>
                                        <div className="input-group" params={ this.props.params }>
                                            <input type="text"
                                                   className="form-control"
                                                   placeholder="joe@yourmail.com sally@yourmail.com devin@yourmail.com"
                                                   params={ this.props.params }></input><span className="input-group-btn " params={ this.props.params }><a href="#"
                                                                                                                                                                                                                                                                     className="btn btn-success"
                                                                                                                                                                                                                                                                     type="submit"
                                                                                                                                                                                                                                                                     params={ this.props.params }><span params={ this.props.params }>Go</span></a>
                                            </span>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            );
    }
}

export default App;

