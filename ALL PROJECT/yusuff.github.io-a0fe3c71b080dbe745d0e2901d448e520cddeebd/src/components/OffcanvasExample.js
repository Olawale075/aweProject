import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Offcanvas from 'react-bootstrap/Offcanvas';

function OffcanvasExample() {
  return (
    <>
      {[false].map((expand) => (
        <Navbar key={expand} bg="dark" variant="dark" expand={expand} className="mb-1">
          <Container fluid>
            <Navbar.Brand className='text-light'variant="dark" href="/home">Yusuff.com</Navbar.Brand>
            <Navbar.Toggle variant="dark" aria-controls={`offcanvasNavbar-expand-${expand}`} />
            <Navbar.Offcanvas variant="dark" 
              id={`offcanvasNavbar-expand-${expand}`}
              aria-labelledby={`offcanvasNavbarLabel-expand-${expand}`}
              placement="end"
            >
              <Offcanvas.Header variant="dark" closeButton>
                <Offcanvas.Title variant="dark" id={`offcanvasNavbarLabel-expand-${expand}`}>
              Yusuff.com
                </Offcanvas.Title>
              </Offcanvas.Header>
              <Offcanvas.Body className=' btn bdarktn- bg-dark'>
                <Nav className="justify-content-end  flex-grow-4 pe-3">
                  <Nav.Link className=' btn btn-secondary text-light' href="/home">Home</Nav.Link>
                  <Nav.Link className=' btn btn-secondary text-light' href="/about">About</Nav.Link>
                  <Nav.Link className=' btn btn-secondary text-light' href="/contact">Contact</Nav.Link>
                  <Nav.Link className=' btn btn-secondary text-light ' href="/bubu">Bubu style</Nav.Link>
                  <Nav.Link className=' btn btn-secondary text-light ' href="/gown">Gown style</Nav.Link>   
                  <Nav.Link className=' btn btn-secondary text-light ' href="/blouse">Blouse style</Nav.Link>
                  <Nav.Link className=' btn btn-secondary text-light ' href="/free">Free style</Nav.Link>

                  <Nav.Link className=' btn btn-secondary text-light ' href="/manwear">ManWears style</Nav.Link>

                </Nav>
              
              </Offcanvas.Body>
            </Navbar.Offcanvas>
          </Container>
        </Navbar>
      ))}
    </>
  );
}

export default OffcanvasExample;