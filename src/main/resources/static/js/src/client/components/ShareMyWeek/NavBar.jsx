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
                    <Navbar.Header>
                        <NavbarBrand>
                            <a href="#"><span>202Forever</span></a>
                        </NavbarBrand>
                        <Navbar.Toggle/>
                    </Navbar.Header>
                    <Navbar.Collapse>
                        <Nav pullRight={ true }>
                            <NavItem href="/">
                                <span>Home</span>
                            </NavItem>
                            <NavItem href="http://cs580.yusun.io/teams-winter2016/202Forever/index.html">
                                <span>Contact Us</span>
                            </NavItem>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            );
    }
}

export default NavBar;
