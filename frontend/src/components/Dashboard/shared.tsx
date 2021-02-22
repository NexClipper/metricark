import React, { ReactNode } from "react";
import { x } from "@xstyled/emotion";

export const SimpleCard: React.FC<{
  col?: number;
  title: ReactNode;
  props?: any;
  cardProps?: any;
}> = ({ col, title, children, cardProps, ...props }) => {
  return (
    <x.div col={col ?? 1 / 5} px={10} {...props}>
      <Card title={title} {...cardProps}>
        {children}
      </Card>
    </x.div>
  );
};

const Card: React.FC<{ title: ReactNode }> = ({ title, children }) => {
  return (
    <x.div backgroundColor="white" border="1px solid #f0f0f0">
      <x.div display="flex" borderBottom="1px solid #ededed">
        <x.div flex="1 1" p="18px" alignItems="center" fontSize="1.05rem">
          {title}
        </x.div>
      </x.div>
      <x.div p="18px" display="block">
        {children}
      </x.div>
    </x.div>
  );
};
