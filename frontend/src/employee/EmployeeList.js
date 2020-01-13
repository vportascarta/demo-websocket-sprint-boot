import React, {useState} from 'react';
import {Link} from "react-router-dom";
import makeStyles from "@material-ui/core/styles/makeStyles";
import useSTOMPWebsocket from "../utils/useSTOMPWebsocket";
import useEventSource from "../utils/useEventSource";
import {addRandom, deleteId, lockId, unlockId} from "../utils/API";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import moment from "moment";
import TableFooter from "@material-ui/core/TableFooter";
import TablePagination from "@material-ui/core/TablePagination";
import LinearProgress from "@material-ui/core/LinearProgress";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from '@material-ui/icons/Delete';
import ModifyIcon from '@material-ui/icons/Edit';
import Skeleton from '@material-ui/lab/Skeleton';
import clsx from 'clsx';
import Typography from "@material-ui/core/Typography";
import {editEmployee} from "../app/Routes";

const useStyles = makeStyles({
  app: {
    margin: 10,
    minWidth: 750,
  },
  toolbar: {
    padding: 10
  },
  button: {
    marginRight: 10
  },
  floatRight: {
    float: 'right'
  },
  emptyProgress: {
    minHeight: 4
  },
  row: {
    height: 45
  },
  unlock: {
    color: '#fff'
  },
  lock: {
    color: '#f00'
  },
  deleted: {
    color: '#888'
  }
});


const EmployeeList = () => {
  const [isLoading, setLoading] = useState(true);
  const [progression, setProgression] = useState(0);
  const [employees, setEmployees] = useState([]);
  const [showDeleted, setShowDeleted] = useState(true);

  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  useEventSource('http://localhost:8000/employee/all',
    [
      {
        type: "GET_ALL",
        callback: evt => setEmployees(prevState => prevState.concat(JSON.parse(evt.data)))
      },
      {
        type: "PROGRESSION",
        callback: evt => setProgression(JSON.parse(evt.data))
      },
    ],
    () => {
      setProgression(100.0);
      setLoading(false);
    },
  );

  useSTOMPWebsocket('ws://localhost:8000/ws', [
    {
      url: '/topics/employee',
      callback: message => {
        const data = JSON.parse(message.body);
        setEmployees(prevState => {
          const newArray = JSON.parse(JSON.stringify(prevState));
          const i = newArray.findIndex(_item => _item.id === data.id);
          if (i > -1) newArray[i] = data;
          else newArray.push(data);

          return newArray;
        })
      }
    },
  ]);

  const classes = useStyles();
  const rows = showDeleted ? employees : employees.filter(value => !value.deleted);
  const emptyRows = [...Array(rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage)).keys()];

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = event => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <div className={classes.app}>
      <Typography variant='h2' component="h2">
        Liste des employés
      </Typography>
      <Paper>
        <div className={classes.toolbar}>
          {isLoading ? <LinearProgress variant="determinate" value={progression}/> :
            <div className={classes.emptyProgress}/>}
          <div className={classes.emptyProgress}/>
          <Button className={classes.button} onClick={() => window.location.reload(true)}
                  variant="contained">Rafraichir</Button>
          <Button className={classes.button} onClick={() => setShowDeleted(prevState => !prevState)}
                  variant="contained">{showDeleted ? "Masquer les inactifs" : "Afficher les inactifs"}</Button>
          <Button className={classes.floatRight} onClick={addRandom} variant="contained">Ajout Aléatoire</Button>
        </div>
        <TableContainer>
          <Table size="small" aria-label="employee table">
            <TableHead>
              <TableRow>
                <TableCell>Matricule</TableCell>
                <TableCell align="right">Prénom</TableCell>
                <TableCell align="right">Nom</TableCell>
                <TableCell align="right">Date d'ancienneté</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPage > 0
                  ? rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  : rows
              ).map(row => (
                <TableRow key={row.id} className={classes.row}>
                  <TableCell
                    className={clsx(row.locked ? classes.lock : classes.unlock, row.deleted && classes.deleted)}>{row.matricule}</TableCell>
                  <TableCell
                    className={clsx(row.locked ? classes.lock : classes.unlock, row.deleted && classes.deleted)}
                    align="right">{row.firstName}</TableCell>
                  <TableCell
                    className={clsx(row.locked ? classes.lock : classes.unlock, row.deleted && classes.deleted)}
                    align="right">{row.lastName}</TableCell>
                  <TableCell
                    className={clsx(row.locked ? classes.lock : classes.unlock, row.deleted && classes.deleted)}
                    align="right">{moment(row.seniorityDate).format('llll')}</TableCell>
                  <TableCell align="right">
                    {!row.deleted &&
                    <>
                      <IconButton disabled={row.locked} size="small" aria-label="modify" component={Link} to={editEmployee(row.id)}>
                        <ModifyIcon/>
                      </IconButton>
                      <IconButton disabled={row.locked} onClick={() => deleteId(row.id)} size="small" aria-label="delete">
                        <DeleteIcon/>
                      </IconButton>
                    </>
                    }
                  </TableCell>
                </TableRow>
              ))}
              {emptyRows.length > 0 ? emptyRows.map(row => (
                <TableRow key={`empty_${row}`} className={classes.row}>
                  <TableCell>{isLoading ? <Skeleton variant="text"/> : null}</TableCell>
                  <TableCell>{isLoading ? <Skeleton variant="text"/> : null}</TableCell>
                  <TableCell>{isLoading ? <Skeleton variant="text"/> : null}</TableCell>
                  <TableCell>{isLoading ? <Skeleton variant="text"/> : null}</TableCell>
                  <TableCell>{isLoading ? <Skeleton variant="text"/> : null}</TableCell>
                </TableRow>
              )) : null}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TablePagination
                  rowsPerPageOptions={[5, 10, 25, {label: 'All', value: -1}]}
                  colSpan={5}
                  count={rows.length}
                  rowsPerPage={rowsPerPage}
                  page={page}
                  SelectProps={{
                    inputProps: {'aria-label': 'rows per page'},
                    native: true,
                  }}
                  onChangePage={handleChangePage}
                  onChangeRowsPerPage={handleChangeRowsPerPage}
                />
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>
      </Paper>
    </div>
  );
};

export default EmployeeList;
