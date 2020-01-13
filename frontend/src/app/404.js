import React from 'react';
import Typography from '@material-ui/core/Typography';
import {Link} from 'react-router-dom';
import * as ROUTES from './Routes';
import Button from '@material-ui/core/Button';

const Page404 = () => {
  return (
    <div style={{textAlign: 'center'}}>
      <Typography variant='h2' component="h2">
        Page non disponible !
      </Typography>
      <div style={{margin: 40}}/>
      <Button variant='contained' color='secondary' component={Link} to={ROUTES.WELCOME}>
        Accueil
      </Button>
    </div>
  )
};

export default Page404;
