import React from "react";
import { useRouteMatch } from "react-router-dom";
import { x } from "@xstyled/emotion";

import { QueryFields } from "./types";

const KubQueryDetail: React.FC<{ data?: any; field: QueryFields }> = ({
  data,
}) => {
  const {
    params: { podname },
  } = useRouteMatch<{ podname: string }>();

  return (
    <x.div p={20}>
      <div>{podname}</div>
      <div>{JSON.stringify(data)}</div>
    </x.div>
  );
};

export default KubQueryDetail;
