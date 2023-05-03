/** @format */

import Table from 'react-bootstrap/Table';
import { AiOutlineEdit, AiOutlineDelete } from 'react-icons/ai';

function TableComponent() {
  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>S/N</th>
          <th>Short Name</th>
          <th>Message Type</th>
          <th>Action (Edit and Delete)</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>1</td>
          <td>Tobi</td>
          <td>08000</td>
          <tr>
            <td>
              <AiOutlineEdit />
            </td>
            <td>
              <AiOutlineDelete />
            </td>
          </tr>
        </tr>
        <tr>
          <td>2</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>3</td>
          <td></td>
          <td></td>
        </tr>
      </tbody>
    </Table>
  );
}

export default TableComponent;
