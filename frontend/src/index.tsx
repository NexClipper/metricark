import React from "react";
import ReactDOM from "react-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";

import Dashboard from "components/Dashboard";

import Layout from "./components/Layout";

import "antd/dist/antd.css";
import "./index.css";

const queryClient = new QueryClient();

ReactDOM.render(
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <Switch>
        <Layout>
          <Route path="/">
            <Dashboard />
          </Route>
        </Layout>
      </Switch>
      <Redirect to="/" />
    </BrowserRouter>
  </QueryClientProvider>,
  document.getElementById("root")
);
