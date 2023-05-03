import React from 'react'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';

const HeaderComponent = () => {
  return (
    <Navbar bg="dark " variant="dark">
    <Container>
      <Navbar.Brand href="home">Olawale.com</Navbar.Brand>
      <Nav className=" ml-auto">
        <Nav.Link href="home">Home</Nav.Link>
        <Nav.Link href="about">about</Nav.Link>
        <Nav.Link href="contact">Contact</Nav.Link>
        <ul className="nav navbar-nav navbar-right">
       <li><a href="/about">Sign Up</a></li>
       <li><a href="/">Sign In</a></li>
       <li className="dropdown">
          <a href="/" className="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">More<span class="caret"></span></a>
          <ul className="dropdown-menu">
            <li><a href="/">Start Learning</a></li>
            <li><a href="/">View All Courses</a></li>
            <li><a href="/">Chat with a CodeGuide</a></li>
          </ul>
        </li>     </ul>
      </Nav>
    </Container>
  </Navbar>
  )
}

export default HeaderComponent