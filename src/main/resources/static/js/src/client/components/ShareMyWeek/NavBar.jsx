import React, { Component, PropTypes } from 'react';
import { Navbar, NavbarBrand, Nav, NavItem } from 'react-bootstrap';

class NavBar extends Component {

    constructor(props, content) {
        super(props, content);
        this.handleOnClick = this.handleOnClick.bind(this);
        this.handleOnChange = this.handleOnChange.bind(this);
    }
    handleOnClick(e) {
        e.preventDefault();
        e.stopPropagation();
    }
    handleOnChange() {
    }

    render() {
        return (<Navbar
                        {...this.props}
                        staticTop={ true }>
                    <NavbarBrand>
                        <a href="#"><span>202Forever</span></a>
                    </NavbarBrand>
                    <Nav pullRight={ true }>
                        <NavItem
                                 href="#"
                                 active={ true }>
                            <span>Home</span>
                        </NavItem>
                        <NavItem href="#">
                            <span>Contact Us</span>
                        </NavItem>
                    </Nav>
                </Navbar>
            );
    }
}

export default NavBar;
