import React from "react";
import { useQuery } from "react-query";
import { Route, Switch, useRouteMatch } from "react-router-dom";
import { Alert, Spin } from "antd";

import request from "../Dashboard/request";
import { SimpleCard } from "../Dashboard/shared";

import PodList from "./List";
import Pod from "./Pod";

function Pods() {
  const { path } = useRouteMatch();
  // @ts-ignore
  const { isLoading, error, data: response } = useQuery(
    "pods",
    () => request.pods(),
    {
      notifyOnChangeProps: ["data"],
    }
  );

  if (error) {
    return (
      <SimpleCard title="">
        <Alert message="Server error" type="error" showIcon />
      </SimpleCard>
    );
  }

  if (isLoading || !response?.data) {
    return (
      <SimpleCard title="">
        <Spin />
      </SimpleCard>
    );
  }

  const { data } = response.data;

  return (
    <Switch>
      <Route path={`${path}/:podname`} exact>
        <Pod data={data} />
      </Route>
      <Route path={`${path}`} exact>
        <PodList data={data} />
      </Route>
    </Switch>
  );
}

export default Pods;
