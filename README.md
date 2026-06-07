# create-scala-app

> **Dev Journal** — Until a proper release happens, this file will serve as a running development journal, tracking the journey and the latest updates.

---

## Table of Contents

- [The Vision](#the-vision)
- [Development Philosophy & Challenges](#development-philosophy--challenges)
- [Why the Silence?](#why-the-silence)
- [Dev Log](#dev-log)
- [What Building This Is Teaching Me](#what-building-this-is-teaching-me)

---

## The Vision

The tool is taking much more time than anticipated — with each new addition, new ideas and new ambitions emerge.

The end goal is to have the tool work like this:

```bash
$ create-scala-app my-service

? HTTP layer       › http4s | smithy4s | tapir
? Database         › skunk (postgres) | doobie (postgres) | none
? Auth             › JWT | session | none
? Migrations       › Flyway | none
? Observability    › otel4s + Prometheus | none
? Test framework   › munit-cats-effect | weaver

✔ Generated my-service/
✔ Dependencies resolved (Scala 3.4, CE 3.5)
✔ App boots — run: cd my-service && sbt run
```

However, this requires much more work than initially anticipated. So the **first version** will be a trimmed, complete template of the (almost) standard-practice stack for each ecosystem in Scala:

```bash
$ create-scala-app my-service

template    › typelevel | zio | akka | ...other

orgname     ...

FLAGS: compile, run ... ??
```

This first version will essentially be a complete, working, batteries-included template that is tested, compiled, and ships with many ready-to-use features:

- Auth token implementation
- Running server
- Proper middlewares
- CORS policy
- API docs
- Email verification *(maybe — more thinking needed on what to include and what to exclude)*

---

## Development Philosophy & Challenges

Many challenges arise during the development of this tool, one of which is rooted in the philosophy of Scala itself.

Scala is amazing at being modularized and offering many options to choose and compose from. Trying to replicate other tools or frameworks — like Django — necessarily means imposing rules and restrictions on what you can and cannot edit. In Scala, we simply don't have things like that. I can't force you to use an ORM instead of direct SQL (notably, doobie — one of the options in this very tool — is explicitly not an ORM; it's a functional layer over raw SQL), or impose some new paradigm.

So the tool will be a **scaffolding** of already widely used and battle-tested patterns in Scala. The end goal is to give developers a scaffold of a ready-to-run template with many batteries-included modules — the kind they'd otherwise have to build from scratch every single time — while also giving them the freedom to switch, edit, or even delete all of those hard-coded modules (yes, it hurts a little).

Most notably, the generated app must:

- Be ready to launch out of the box
- Have proper documentation for the already-written modules
- Include configuration modules for handling configuration, auth, logging, and databases

I also intend to add features such as making it easy to run cron jobs or compose and run streams out of the box.

Each template must have a proper README and docs to introduce the already-written code, along with instructions on what can be safely edited and what might break if changed. The goal is to make each template as **modular as possible** — where if an implementation is not desired, a simple swap of that implementation (as long as it complies with the existing API) requires no other changes.

---

## Why the Silence?

Why all that delay without any update to the repo, you ask? Well, a lot happened over these past few months — most notably medical school graduation and some health-related issues that required surgery. But the real reason there have been no commits while I'm still actively coding is... I'm just lazy about pushing. 🙂

---

## Dev Log

I'll be sharing what updates are being made and why I chose one thing over another in an organized folder at `devLog/`. You'll just have to wait for my lazy self to push the updates.

---

## What Building This Is Teaching Me

Developing this tool is genuinely harder than expected, and it demands a deeper level of skill than I initially gave it credit for.

For example, while developing the template for the Typelevel stack, I kept discovering new ways to solve problems and uncovering new information about libraries I thought I already knew. My prior experience with that stack turned out to be quite surface-level. "I want middleware? Sure, let me copy this from the docs, tweak a few things, and hope it works." Meanwhile, I had no real idea what a `Kleisli` was — but I kept seeing the word pop up in red.

When you see `Kleisli` for the thousandth time in the error logs, you stop and ask yourself one honest question: **WTF is Kleisli?!**

So you go investigate what kind of black magic this thing is — and this happened with many libraries along the way, each requiring a deep dive to find real answers — and then... *drum roll*...

**It's just a case class.**(of course wrapping a function to allow monadic composition)

All that suffering caused by a case class and type abstraction. Developing this project forces you to genuinely understand what you're working with and *why*. It teaches you how to compose types efficiently — how to take `Either`, `Option`, and other types and combine them into a single type that short-circuits on failure and produces a meaningful error message.

---

*More updates coming — once I push them.*
