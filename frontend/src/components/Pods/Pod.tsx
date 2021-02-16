import React from "react";
import { x } from "@xstyled/emotion";

const Pod: React.FC<{ data: any }> = ({ data }) => {
  return (
    <x.div p={20}>
      <x.div>{JSON.stringify(data)}</x.div>
    </x.div>
  );
};

export default Pod;
