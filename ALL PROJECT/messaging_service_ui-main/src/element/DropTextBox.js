/** @format */

import Dropdown from 'react-bootstrap/Dropdown';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';



function DropTextBox() {
  return (
    <>
      <Dropdown>
        <Dropdown.Toggle id="dropdown-button-dark-example1" variant="secondary">
          Message Type
        </Dropdown.Toggle>

        <Dropdown.Menu variant="dark">
          <Dropdown.Item href="#/action-1" active>
            Action
          </Dropdown.Item>
          <Dropdown.Item href="#/action-2">Another action</Dropdown.Item>
          <Dropdown.Item href="#/action-3">Something else</Dropdown.Item>
          <Dropdown.Divider />
          <Dropdown.Item href="#/action-4">Separated link</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>

      <Row>
        <Form.Label column lg={2}>
          Enter Message Short Name:
        </Form.Label>
        <Col>
          <Form.Control type="text" placeholder="Normal text" />
        </Col>
      </Row>
    </>
  );
}

export default DropTextBox;
