import React, {useEffect, useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import {getOne, lockId, unlockId, update} from "../utils/API";
import {EMPLOYEE} from "../app/Routes";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";
import makeStyles from "@material-ui/styles/makeStyles";

const useStyles = makeStyles(theme => ({
  root: {
    '& > *': {
      margin: theme.spacing(2),
      width: 500,
    },
  },
}));

const EditEmployee = () => {
  const classes = useStyles();
  const [isLoading, setLoading] = useState(true);
  const [isLocked, setLocked] = useState(false);
  const [employee, setEmployee] = useState({});
  const history = useHistory();
  const {id} = useParams();

  useEffect(() => {
    lockId(id).then(res => {
      if (res.ok) {
        setLocked(true);
        window.onbeforeunload = () => {
          unlockId(id).then(() => {
          });
          return null;
        };

        getOne(id).then(res => {
          if (res.ok) {
            res.json().then(data => {
              setEmployee(data);
              setLoading(false);
            });
          }
        });
      } else {
        history.push(EMPLOYEE)
      }
    });

    return () => {
      if (!isLocked) {
        unlockId(id).then(() => {
        });
        window.onbeforeunload = () => {
          return null;
        };
      }
    };
  }, [id]);

  return (
    <>
      <Typography variant='h2' component="h2">
        {`Edition employé : ${id}`}
      </Typography>
      {!isLoading ?
        <div className={classes.root} noValidate autoComplete="off">
          <TextField name="matricule" label="Matricule" variant="outlined" value={employee.matricule} onChange={evt => setEmployee(Object.assign({}, employee, {matricule: evt.target.value}))}/>
          <TextField name="firstname" label="Prénom" variant="outlined" value={employee.firstName} onChange={evt => setEmployee(Object.assign({}, employee, {firstName: evt.target.value}))}/>
          <TextField name="lastname" label="Nom" variant="outlined" value={employee.lastName} onChange={evt => setEmployee(Object.assign({}, employee, {lastName: evt.target.value}))}/>
          <Button onClick={() => update(employee).then((res) => {if(res.ok) history.push(EMPLOYEE)})} variant="outlined">Valider</Button>
        </div>
        : <CircularProgress/>}
    </>
  )
};

export default EditEmployee;
