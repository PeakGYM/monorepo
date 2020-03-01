module Steps = {
  [@react.component] [@bs.module "antd"]
  external make:
    (
      ~current: int=?,
      ~progressDot: bool=?,
      ~direction: string=?,
      ~children: React.element=?
    ) =>
    React.element =
    "Steps";
};

module Step = {
  [@react.component] [@bs.module "antd"] [@bs.scope "Steps"]
  external make: (~title: string=?, ~description: string=?) => React.element =
    "Step";
};