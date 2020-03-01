module Steps = {
  [@react.component] [@bs.module "antd"]
  external make:
    (~current: int=?, ~direction: string=?, ~children: React.element=?) =>
    React.element =
    "Steps";
};

module Step = {
  [@react.component] [@bs.module "antd"] [@bs.scope "Steps"]
  external make:
    (~title: React.element=?, ~description: React.element=?) => React.element =
    "Step";
};