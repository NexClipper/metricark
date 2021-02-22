import React from "react";
import { Redirect, Route, Switch, useRouteMatch } from "react-router-dom";

import KubQueryDashboard from "./Dashboard";
import KubQueryDetail from "./Detail";
import KubQueryList from "./List";
import { QueryFields } from "./types";

function KubQuery() {
  const { path } = useRouteMatch();

  return (
    <Switch>
      <Route path={`${path}/:field`}>
        <Field />
      </Route>
      <Route path={path} exact>
        <KubQueryDashboard />
      </Route>
      <Redirect to={path} />
    </Switch>
  );
}

const Field = () => {
  const {
    path,
    params: { field },
  } = useRouteMatch<{ field: QueryFields }>();

  return (
    <Switch>
      <Route path={`${path}/:detailName`} exact>
        <KubQueryDetail field={field} />
      </Route>
      <Route path={path} exact>
        <KubQueryList field={field} />
      </Route>
      <Redirect to={path} />
    </Switch>
  );
};

export default KubQuery;
