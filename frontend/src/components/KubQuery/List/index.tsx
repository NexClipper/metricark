import React from "react";

import QueryRequest from "../QueryRequest";
import { QueryFields } from "../types";

import Daemonsets from "./Daemonsets";
import Deployments from "./Deployments";
import Nodes from "./Nodes";
import Pods from "./Pods";
import Replicasets from "./Replicasets";
import Services from "./Services";
import Statefulsets from "./Statefulsets";

const Logger: React.FC<{ data: any }> = ({ data }) => {
  return <pre>{JSON.stringify(data, null, 4)}</pre>;
};

const COMPONENTS: { [K in QueryFields]: React.FC<{ data: any }> } = {
  pods: Pods,
  deployments: Deployments,
  daemonsets: Daemonsets,
  nodes: Nodes,
  replicasets: Replicasets,
  services: Services,
  statefulsets: Statefulsets,
} as const;

const KubQueryList: React.FC<{ field: QueryFields }> = ({ field }) => {
  if (COMPONENTS[field]) {
    return (
      <QueryRequest component={COMPONENTS[field]} type="list" field={field} />
    );
  }

  return <QueryRequest component={Logger} type="list" field={field} />;
};

export default KubQueryList;
